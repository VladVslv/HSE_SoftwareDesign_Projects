package restaurant.agents;

import jade.lang.acl.UnreadableException;
import restaurant.model.Dish;
import restaurant.configuration.JadeAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import restaurant.util.JsonMessage;
import restaurant.util.SimpleNameFormatter;
import restaurant.util.json.JsonHandler;
import restaurant.util.json.load_templates.process.ProcessLog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

// Class of customer agent
@JadeAgent(number = 10)
public class CustomerAgent extends SuperAgent {

    // Order of customer
    ArrayList<Dish> order = new ArrayList<>();
    String orderTime;

    @Override
    protected void setup() {
        connect();
        createOrder();
        addBehaviour(new CustomerServer());
    }

    @Override
    protected void takeDown() {
        disconnect();
    }

    // Method to ask for menu and generate new order
    private void createOrder() {
        JsonMessage menuAccessMessage = new JsonMessage(ACLMessage.CONFIRM);
        ACLMessage answer = sendAndReceiveFromAgent(ManagerAgent.class.getName(), menuAccessMessage);
        JsonMessage orderMessage = new JsonMessage(ACLMessage.PROPOSE);
        orderMessage.addReceiver(answer.getSender());
        try {
            var random = new Random();
            var waitingTime = random.nextDouble() + 1;
            var menu = (ArrayList<Dish>) answer.getContentObject();
            Collections.shuffle(menu);
            int maxOrderSize = menu.size() > 1 ? random.nextInt(menu.size() - 1) + 1 : 1;
            for (int i = 0; i < maxOrderSize; ++i) {
                if (menu.get(i).getTime() <= waitingTime + 1) {
                    order.add(menu.get(i));
                }
            }
        } catch (UnreadableException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        try {
            orderMessage.setContentObject(order);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        orderTime = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        StringBuilder log = new StringBuilder(SimpleNameFormatter.getSimpleName(getName()) + " created order: ");
        for (Dish dish : order) {
            log.append("\"").append(dish.getName()).append("\", ");
        }
        logger.info(log.substring(0, log.length() - 2));
        send(orderMessage);
    }

    // Behaviour of customer agent
    // AGREE - receive cooked order
    // CANCEL - receive info about cancelled order
    private class CustomerServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate messageTemplate = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE),
                    MessageTemplate.MatchPerformative(ACLMessage.CANCEL));
            ACLMessage msg = myAgent.blockingReceive(messageTemplate);
            Dish dish;
            try {
                dish = (Dish) msg.getContentObject();
            } catch (UnreadableException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            if (msg.getPerformative() == ACLMessage.CANCEL) {
                logger.info(SimpleNameFormatter.getSimpleName(getName()) + " did not receive \"" + dish.getName() + "\"");
            } else {
                logger.info(SimpleNameFormatter.getSimpleName(getName()) + " received \"" + dish.getName() + "\"");
                JsonHandler.addProcessLog(new ProcessLog(null, dish.getId(),
                        orderTime, new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime()),
                        false));
            }

            order.remove(dish);
            if (order.size() == 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
                createOrder();
            }
        }
    }
}
