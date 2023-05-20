package restaurant.agents;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.UnreadableException;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import restaurant.model.Dish;
import restaurant.model.Recipe;
import restaurant.util.SimpleNameFormatter;

import java.io.IOException;

// Agent of cooking process of dish
public class CookingProcessAgent extends SuperAgent {

    // Dish to cook
    Dish dish;

    // Customer of this dish
    AID customer;

    CookingProcessAgent(Dish dish, AID customer) {
        this.dish = dish;
        this.customer = customer;
    }

    @Override
    protected void setup() {
        logger.info(SimpleNameFormatter.getSimpleName(getName()) + " starts cooking dish");
        connect();
        addBehaviour(new cookingProcessBehaviour());
    }

    @Override
    protected void takeDown() {
        disconnect();
    }

    // Behaviour of this agent
    // Get recipe from menu agent and cook it
    private class cookingProcessBehaviour extends Behaviour {

        boolean is_done = false;

        public void action() {
            ACLMessage menuMessage = new ACLMessage(ACLMessage.REQUEST);
            try {
                menuMessage.setContentObject(dish);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

            Recipe recipe;
            try {
                recipe =
                        (Recipe) (sendAndReceiveFromAgent(MenuAgent.class.getName(), menuMessage).getContentObject());
            } catch (UnreadableException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

            ACLMessage cookingProcess = new ACLMessage(ACLMessage.PROPOSE);

            try {
                cookingProcess.setContentObject(recipe);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

            // Reserve equipment
            AID equipment = sendAndReceiveFromAgent(EquipmentAgent.class.getName() + recipe.getEquipType(),
                    cookingProcess).getSender();

            // Send recipe to cook
            sendAndReceiveFromAgent(CookAgent.class.getName(), cookingProcess);

            // Unreserve equipment
            sendAndReceiveFromAgent(equipment, cookingProcess);

            ACLMessage endCookingMessage = new ACLMessage(ACLMessage.AGREE);
            try {
                endCookingMessage.setContentObject(dish);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            endCookingMessage.addReceiver(customer);
            myAgent.send(endCookingMessage);
            myAgent.doDelete();
        }

        @Override
        public boolean done() {
            return is_done;
        }
    }
}
