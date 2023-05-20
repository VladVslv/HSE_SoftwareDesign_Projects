package restaurant.agents;

import jade.lang.acl.UnreadableException;
import restaurant.configuration.JadeAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import restaurant.model.Dish;
import restaurant.model.Recipe;
import restaurant.util.json.JsonHandler;
import restaurant.util.SimpleNameFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@JadeAgent
public class MenuAgent extends SuperAgent {

    ArrayList<Recipe> recipes;

    @Override
    protected void setup() {
        recipes = JsonHandler.upLoadRecipes();
        connect();
        addBehaviour(new MenuManagerServer());
    }

    @Override
    protected void takeDown() {
        disconnect();
    }

    private class MenuManagerServer extends CyclicBehaviour {

        public void action() {
            MessageTemplate messageTemplate =
                    MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
                            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            ACLMessage msg = myAgent.blockingReceive(messageTemplate);
            disconnect();
            if (msg.getPerformative() == ACLMessage.CONFIRM) {
                sendMenu(msg);
            } else {
                sendRecipe(msg);
            }
            connect();
        }

        private void sendRecipe(ACLMessage msg) {
            ACLMessage reply = msg.createReply();
            Dish dish;
            try {
                dish = (Dish) msg.getContentObject();
            } catch (UnreadableException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            Recipe recipe = null;
            for (Recipe recipeFromBook : recipes) {
                if (Objects.equals(recipeFromBook.getId(), dish.getId())) {
                    recipe = recipeFromBook;
                    break;
                }
            }
            try {
                reply.setContentObject(recipe);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            logger.info(SimpleNameFormatter.getSimpleName(getName()) + " sent recipe for dish \"" + recipe.getName()+"\"");
            myAgent.send(reply);
        }

        private void sendMenu(ACLMessage msg) {
            ACLMessage reply = msg.createReply();
            try {
                reply.setContentObject(recipes);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            logger.info(SimpleNameFormatter.getSimpleName(getName()) + " sent menu to "
                    + SimpleNameFormatter.getSimpleName(msg.getSender().getName()));
            myAgent.send(reply);
        }
    }
}
