package restaurant.agents;

import jade.lang.acl.UnreadableException;
import restaurant.model.Dish;
import restaurant.configuration.JadeAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import restaurant.util.SimpleNameFormatter;

import java.io.IOException;
import java.util.ArrayList;

// Class of manager agent
@JadeAgent
public class ManagerAgent extends SuperAgent {

    // Available menu
    private ArrayList<Dish> menu;

    public ManagerAgent() {
        menu = new ArrayList<>();
    }

    @Override
    protected void setup() {
        connect();
        addBehaviour(new ManagerServer());
    }

    @Override
    protected void takeDown() {
        disconnect();
    }

    // Behaviour of menu agent
    // PROPOSE - accept customer order
    // CONFIRM - send menu
    private class ManagerServer extends CyclicBehaviour {

        public void action() {
            MessageTemplate messageTemplate =
                    MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                            MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
            ACLMessage msg = blockingReceive(messageTemplate);
            disconnect();
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                orderAccept(msg);
            } else {
                sendMenu(msg);
            }
            connect();
        }

        private void sendMenu(ACLMessage msg) {
            ACLMessage menuAccessMessage = new ACLMessage(ACLMessage.CONFIRM);
            ACLMessage reply = msg.createReply();
            try {
                menu = (ArrayList<Dish>) sendAndReceiveFromAgent(MenuAgent.class.getName(), menuAccessMessage).getContentObject();
            } catch (UnreadableException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            try {
                reply.setContentObject(menu);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            logger.info(SimpleNameFormatter.getSimpleName(getName()) + " sent menu to "
                    + SimpleNameFormatter.getSimpleName(msg.getSender().getName()));
            myAgent.send(reply);
        }

        private void orderAccept(ACLMessage msg) {
            ArrayList<Dish> order;
            logger.info(SimpleNameFormatter.getSimpleName(getName()) + " starts preparations for cooking order from customer "
                    + SimpleNameFormatter.getSimpleName(msg.getSender().getName()));
            try {
                order = (ArrayList<Dish>) msg.getContentObject();
            } catch (UnreadableException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            try {
                final String agentName = OrderAgent.class.getSimpleName() + "_" + msg.getSender().getLocalName();
                getContainerController().acceptNewAgent(agentName, new OrderAgent(order, msg.getSender())).start();
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

}
