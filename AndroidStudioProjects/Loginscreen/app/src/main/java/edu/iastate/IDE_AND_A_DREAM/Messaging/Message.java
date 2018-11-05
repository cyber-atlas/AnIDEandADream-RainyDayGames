package edu.iastate.IDE_AND_A_DREAM.Messaging;

public class Message {

    private int timestamp;
    private String messageText;
    private String sender;
    private String receiver;

    private String displayMessage;

    /**
     * Message object with the information it should be storing
     * @param timestamp the time it was sent
     * @param messageText the text that was sent
     * @param sender the person who sent the message
     * @param receiver the person who will be receiving the message
     */
    //TODO maybe add a messageID?
    public Message(int timestamp, String messageText, String sender, String receiver){

        //TODO implement a check to make sure the timestamp is being passed in correctly
        this.timestamp = timestamp;
        //TODO implement a check to ensure the string is not empty
        this.messageText = messageText;
        //TODO implement a check to ensure the sender is valid
        this.sender = sender;
        //TODO implement a check  to ensure the reciever is valid
        this.receiver = receiver;

    }

    private String setDisplayMessage(){
        displayMessage = sender + " \t \t" + timestamp + "\n" + messageText;

        return displayMessage;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDisplayMessage() {
        setDisplayMessage();
        return displayMessage;
    }
}
