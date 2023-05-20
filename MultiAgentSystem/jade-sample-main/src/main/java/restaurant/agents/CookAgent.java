package restaurant.agents;

import jade.lang.acl.UnreadableException;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import restaurant.model.Cook;
import restaurant.model.Recipe;

// Class of cook agent
public class CookAgent extends SuperAgent {

    @Override
    protected void setup() {
        connect();
        addBehaviour(new CookServer());
    }

    // Cook of this agent
    Cook cook;

    public CookAgent(Cook cook) {
        this.cook = cook;
    }

    @Override
    protected void takeDown() {
        disconnect();
    }

    // Behaviour of cook agent
    // Cook dish from an incoming message
    private class CookServer extends CyclicBehaviour {

        public void action() {
            MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
            ACLMessage msg = myAgent.blockingReceive(messageTemplate);
            disconnect();
            if (msg != null) {
                try {
                    Recipe dish_info = (Recipe) (msg.getContentObject());
                    logger.info("CookAgent" + cook.getId() + " cooks \"" + dish_info.getName()+ "\"");
                } catch (UnreadableException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
                ACLMessage reply = msg.createReply();
                myAgent.send(reply);
            } else {
                block();
            }
            connect();
        }
    }
}
