package com.models;
import java.sql.Date;

public class MessageBoxModel {
	int messageID ;
	String messageContent ;
	Date messageDate ;
	private int userId ;
	private int conversationId ;

	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Date getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public void addMessage(){

	}

	public void removeMessage(int id){

	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getConversationId() {
		return conversationId;
	}
	public void setConversationId(int conversationId) {
		this.conversationId = conversationId;
	}


}
