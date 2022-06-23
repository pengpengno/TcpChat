package com.wang.demo.chat;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import resource.Chat_Info;
import resource.Client_Info;
import resource.Communication_Action;
import resource.User_Info;

public class Client_FunctionCenter {

	public Client_Info client_info=null;

	public boolean user_login(String id,String psd) {
		//�ж��û��Ƿ���������˻���Ϣ
		if(!(id.equals(""))  &  !(psd.equals(""))) {
			User_Info login_Info=new User_Info(id,psd,Communication_Action.user_login);
			Client_DataTran client_dt=new Client_DataTran(login_Info);
			client_dt.User_DataTran_send();
	        System.out.println("���ں˶������Ϣ");
	        if(client_dt.us_receive==null) {
	        	System.out.println("�������");
	        	return false;
	        }
	        else {
	        	System.out.println("���ȷ�ϣ���ӭ�û�:"+client_dt.us_receive.NickName);
	        		        	
	        	
	        	Client_DataTran client_gain_fri=new Client_DataTran(new User_Info(id,psd,Communication_Action.gain_frineds));
	        	client_gain_fri.User_DataTran_send();
	        	this.client_info=new Client_Info(client_dt.us_receive,client_gain_fri.ve_us_receive);
	        	System.out.println(client_dt.us_receive.ID+"�ͻ�����Ϣ��ʼ�����"+client_gain_fri.ve_us_receive.get(0).ID);
	        	
	        	return true;
	        	}
	        }
		else {
			JOptionPane.showMessageDialog(null, "��ʽ���������������˺�����");
			return false;
		}
			
	}
	
	public void chat_info_send(Chat_Info chat_info) {
		Client_DataTran cidt_send=new Client_DataTran(chat_info);
		new Thread(cidt_send).start();
	}
	public void chat_info_receive(Chat_Info chat_info) {
		Client_DataTran cidt_receive=new Client_DataTran(chat_info);
		new Thread(cidt_receive).start();
	}
	public Vector gain_message_history(Chat_Info chat_info) {	
			Client_DataTran cidt_gain=new Client_DataTran(chat_info);
			cidt_gain.Chat_DataTran_send();			
			System.out.println("�����¼�ѽ������");
		return cidt_gain.ve_ci_receive;
	}
	public void user_regsiter() {
		
		
		
	}
	public void user_off_line() {
		
	}
	
	public   void  user_del() {
		
	}
	
	public void add_friends() {
		
		
	}
	

}
