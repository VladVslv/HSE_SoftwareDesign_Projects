package restaurant.agents;

import jade.lang.acl.UnreadableException;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.configuration.JadeAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import restaurant.util.json.JsonHandler;
import restaurant.util.SimpleNameFormatter;

import java.util.ArrayList;
import java.util.Objects;

@JadeAgent
public class WarehouseAgent extends SuperAgent {

    ArrayList<Ingredient> ingredients = new ArrayList<>();

    @Override
    protected void setup() {
        ingredients = JsonHandler.upLoadIngredients();
        connect();
        addBehaviour(new WarehouseServer());
    }

    @Override
    protected void takeDown() {
        disconnect();
    }

    private class WarehouseServer extends CyclicBehaviour {

        public void action() {
            MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
            ACLMessage msg = myAgent.blockingReceive(messageTemplate);
            disconnect();
            checkWarehouse(msg);
            connect();
        }

        private void checkWarehouse(ACLMessage msg) {
            Dish dish;
            try {
                dish = (Dish) msg.getContentObject();
            } catch (UnreadableException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            logger.info(SimpleNameFormatter.getSimpleName(getName()) + " check available ingredients for dish \"" + dish.getName() + "\"");
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

            for (Ingredient ingredient : dish.getIngredients()) {
                boolean exist = false;
                for (Ingredient warehouseIngredient : ingredients) {
                    if (Objects.equals(ingredient.getProductId(), warehouseIngredient.getProductId())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    reply.setPerformative(ACLMessage.CANCEL);
                    break;
                }
            }
            myAgent.send(reply);
        }
    }
}
