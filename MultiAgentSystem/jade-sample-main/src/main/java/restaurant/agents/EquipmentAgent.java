package restaurant.agents;

import jade.lang.acl.UnreadableException;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import restaurant.model.Equipment;
import restaurant.model.Recipe;

// Class of equipment agent
public class EquipmentAgent extends SuperAgent {

    @Override
    public void connect() {
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(EquipmentAgent.class.getName() + equipment.getType());
        serviceDescription.setName(getName());

        agentDescription.addServices(serviceDescription);

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    // Is the equipment reserved?
    private boolean reserved = false;

    // Equipment of this agent
    public Equipment equipment;

    public EquipmentAgent(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    protected void setup() {
        connect();
        addBehaviour(new EquipmentServer());
    }

    @Override
    protected void takeDown() {
        disconnect();
    }

    // Behaviour of cook agent
    // Reserve/unreserve after incoming message
    private class EquipmentServer extends CyclicBehaviour {

        public void action() {
            MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
            ACLMessage msg = myAgent.blockingReceive(messageTemplate);
            if (!reserved) {
                disconnect();
            }
            try {
                Recipe dish_info = (Recipe) (msg.getContentObject());
                if (!reserved) {
                    logger.info("EquipmentAgent" + equipment.getType() + " reserved for cooking \""
                            + dish_info.getName() + "\"");
                } else {
                    logger.info("EquipmentAgent" + equipment.getType() + " unreserved from cooking \""
                            + dish_info.getName() + "\"");
                }
            } catch (UnreadableException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

            ACLMessage reply = msg.createReply();
            myAgent.send(reply);
            if (reserved) {
                connect();
            }
            reserved = !reserved;
        }
    }
}
