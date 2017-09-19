package cms.academic.academicapp.model;

public class Mail {
	private String sender;
	private String[] recepients;
	private String subject;
	private String body;
	
	public Mail(String sender) {
		this.sender = sender;
	}

	public String getSender() {
		return sender;
	}

	public String[] getRecepients() {
		return recepients;
	}

	public void setReceiver(String[] recepients) {
		this.recepients = recepients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
    
	public void addReceipients(String recepient, int index){
		for(int i = 0; i < recepient.length(); i++){
			if(recepients[i] == recepient){
				return;
			}
		}
		this.recepients[index] = recepient;
	}
}
