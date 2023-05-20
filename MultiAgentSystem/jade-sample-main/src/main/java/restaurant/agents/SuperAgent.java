package restaurant.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

// Custom agent class
public abstract class SuperAgent extends Agent {

    // Number of messages to generate unique replyWith of message
    private static final AtomicLong lastMessageId = new AtomicLong(0);

    // Logger
    protected static final Logger logger = LoggerFactory.getLogger(SuperAgent.class);

    // Method to deregister agent in DFService
    public void connect() {
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());

        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setType(getClass().getName());
        serviceDescription.setName(getName());
        agentDescription.addServices(serviceDescription);

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Method to deregister agent in DFService
    public void disconnect() {
        try {
            DFService.deregister(this);
        } catch (FIPAException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Method to send message and receive answer from one agent with this service type
     * @param serviceType Type of service
     * @param originalMessage Message without receivers
     * @return Reply message
     */
    public ACLMessage sendAndReceiveFromAgent(String serviceType, ACLMessage originalMessage) {
        ACLMessage msg = (ACLMessage) originalMessage.clone();
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setType(serviceType);
        template.addServices(serviceDescription);

        DFAgentDescription[] agents = new DFAgentDescription[0];

        try {
            do {
                agents = DFService.search(this, template);
            } while (agents.length == 0);
        } catch (FIPAException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }

        return sendAndReceiveFromAgent(agents[0].getName(), msg);
    }

    /**
     * Method to send message and receive answer from agent with this AID
     * @param aid AID of recipient agent
     * @param msg Message without receivers
     * @return Reply message
     */
    public ACLMessage sendAndReceiveFromAgent(AID aid, ACLMessage msg) {
        msg.addReceiver(aid);

        msg.setReplyWith("" + lastMessageId.addAndGet(1));

        send(msg);

        MessageTemplate messageTemplate =
                MessageTemplate.MatchInReplyTo(msg.getReplyWith());

        return blockingReceive(messageTemplate);
    }
}
