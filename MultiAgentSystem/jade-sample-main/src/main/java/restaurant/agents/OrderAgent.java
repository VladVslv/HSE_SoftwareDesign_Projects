package restaurant.agents;

import jade.wrapper.StaleProxyException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import restaurant.model.Dish;

import restaurant.util.SimpleNameFormatter;

import java.io.IOException;
import java.util.ArrayList;

// Class of order
public class OrderAgent extends SuperAgent {

    // Customer of order
    AID customer;

    // Order
    private final ArrayList<Dish> order;

    public OrderAgent(ArrayList<Dish> order, AID customerAgent) {
        customer = customerAgent;
        this.order = order;
    }

    @Override
    protected void setup() {
        logger.info(SimpleNameFormatter.getSimpleName(getName()) + " starts preparations for cooking order");
        connect();
        addBehaviour(new OrderBehaviour());
    }

    @Override
    protected void takeDown() {
        disconnect();
    }


    // Behaviour of order agent
    // Create cook process of each dish
    class OrderBehaviour extends Behaviour {

        boolean is_done = false;

        @Override
        public void action() {
            for (Dish dish : order) {
                ACLMessage warehouseMessage = new ACLMessage(ACLMessage.PROPOSE);
                try {
                    warehouseMessage.setContentObject(dish);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }

                ACLMessage warehouseReply = sendAndReceiveFromAgent(WarehouseAgent.class.getName(), warehouseMessage);
                ACLMessage reply = new ACLMessage(ACLMessage.AGREE);

                try {
                    reply.setContentObject(dish);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }

                // Check if dish ingredients is available in stock
                if (warehouseReply.getPerformative() == ACLMessage.CANCEL) {
                    reply.setPerformative(ACLMessage.CANCEL);
                    reply.addReceiver(customer);
                    send(reply);
                } else {
                    try {
                        final String agentName =
                                CookingProcessAgent.class.getSimpleName() + "_" + customer.getLocalName() + "_" + dish.getId();
                        getContainerController().acceptNewAgent(agentName, new CookingProcessAgent(dish, customer)).start();
                    } catch (StaleProxyException e) {
                        logger.error(e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }
            is_done = true;
            myAgent.doDelete();
        }

        @Override
        public boolean done() {
            return is_done;
        }
    }
}

