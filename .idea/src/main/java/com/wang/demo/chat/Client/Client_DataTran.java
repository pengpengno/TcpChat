package com.wang.demo.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import resource.Chat_Info;
import resource.Client_Info;
import resource.Communication_Action;
import resource.Heart_Beat;
import resource.User_Info;

/**
 * @author ????
 *
 */
public class Client_DataTran implements Runnable{
	public User_Info us_receive=null;
	public User_Info us_send=null;
	public Chat_Info ci_send=null;
	public Chat_Info ci_receive=null;
	public Object ob_receive=null;

	public Vector<User_Info> ve_us_receive=null;	
	public Vector<User_Info> ve_us_send=null;
	public Vector<Chat_Info> ve_ci_receive=null;
	public Vector<Chat_Info> ve_ci_send=null;

	private ObjectOutputStream oos=null;
	private ObjectInputStream ois=null;
	private Socket s=null;
	private final static String IP="150.158.113.254";
	private final static int PORT=10001;

	/*
	 * ????????????
	 * Client_DataTran  ?? ??????????     ???????? ??????????????????????????User_Info??????????????????????Chat_Info??
	 * 
	 */
	
	

	public Client_DataTran(User_Info us){
		try {
			s=new Socket(IP,PORT);
			System.out.println("?????????????????????????");
			us_send=us;
			
	
		} catch (IOException e1) {
			// TODO ???????? catch ??
			 System.out.println("?????????????????????");
		}	
	}

	public Client_DataTran(Chat_Info ci){
		try {
			s=new Socket(IP,PORT);
			System.out.println("????????????????????????");
			ci_send=ci;	
		} catch (IOException e1) {
			// TODO ???????? catch ??
			 System.out.println("?????????????????????");
		}	
	}

	
	//??????????????
	public void User_DataTran_send() {		
			try {
				oos=new ObjectOutputStream(this.s.getOutputStream());
				oos.writeObject(this.us_send);
				System.out.println("???????????????");
				DataTran_receive();
				User_Info_TypeJudge(ob_receive);
				oos.close();
				ois.close();
				s.close();
				
			} catch (IOException e ) {
				// TODO ???????? catch ??
				e.printStackTrace();
			}					
	}
	
	//??????????
	public void Chat_DataTran_send() {	
			
				try {
					oos=new ObjectOutputStream(this.s.getOutputStream());
					oos.writeObject(ci_send);
				
					System.out.println("?????????????");	
					DataTran_receive();
					Chat_Info_TypeJudge(ob_receive);
					oos.close();
					ois.close();
					s.close();
				} catch (IOException e) {
					// TODO ???????? catch ??
					System.out.println("?????????????????");
					}
			
		
	}
	//???????????
	public void DataTran_receive() {
					
				try {		
					ois=new ObjectInputStream(this.s.getInputStream());
					this.ob_receive=ois.readObject();
					System.out.println("??????????????????");
					
					
				} catch (IOException | ClassNotFoundException e) {
					// TODO ???????? catch ??
					System.out.println("?????????????????");

				}
			
		}
	/*
	 * 
	 * ????????  ??
	 */
	public void User_Info_TypeJudge(Object ob) {
		if(ob instanceof User_Info) {
			this.us_receive=(User_Info) ob;
		}

		else if (ob instanceof Vector) {
			this.ve_us_receive=(Vector<User_Info>) ob;
			System.out.println("????????????????");
			
			
			
		}	
	}
	/*
	 * 
	 *  ?????????
	 */
	public void Chat_Info_TypeJudge(Object ob) {		
		if(ob instanceof Chat_Info) {
			this.ci_receive=(Chat_Info) ob;

		}
		else if (ob instanceof Vector) {
			this.ve_ci_receive=(Vector<Chat_Info>) ob;
			
		}	
	}
	/*
	 * 
	 * ???TCP???????
	 * ???? ??????????  ??????????????????????????
	 */
	public void Chat_Keep() {
		try {
			oos=new ObjectOutputStream(this.s.getOutputStream());
			oos.writeObject(this.ci_send);
			System.out.println(ci_send.sender+"??????????????");
			
			ois=new ObjectInputStream(this.s.getInputStream());
			this.ob_receive=ois.readObject();
			Chat_Info_TypeJudge(ob_receive);
		} catch (IOException | ClassNotFoundException e) {
			// TODO ???????? catch ??
			e.printStackTrace();
		}
			}
	
	@Override
	//???????????ж?
	public void run() {
		
		if(us_send!=null) {
			User_DataTran_send();
		}
		else if (ci_send.command==Communication_Action.keep_receive) {			
			Chat_Keep();		
		}
		else {
			 Chat_DataTran_send();
		}
			
	}
	
}
