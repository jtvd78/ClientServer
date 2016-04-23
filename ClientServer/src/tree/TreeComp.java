package tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import client.Client;
import client.Server;
import client.ServerManager;
import client.User;

public class TreeComp extends JComponent{
	
	int nodeHeight = 25;
	int levelSpacing = 15;
	int boxSize = 9;
	
	Color flashColor = Color.blue;
	int flashTime = 500; //ms
	boolean flashOn = false;
	
	Tree tree;
	Node servers;
	Node transfers;
		Node downloads;
		Node uploads;
	Node selectedNode = null;
	
	ServerPopupMenu serverPopupMenu;
	
	public TreeComp(){
		
		initTree();
		
		Listener l = new Listener();
		addMouseListener(l);
		addMouseMotionListener(l);
		
		serverPopupMenu = new ServerPopupMenu();
		
//		new Thread(new FlashTimer()).start();
	}
	
	private void initTree(){
		tree = new Tree();
		
		//Add Nodes
		servers = new Node("Servers");
		transfers = new Node("File Transfers");
			uploads = new Node("Uploads");
				transfers.addNode(uploads);
			downloads = new Node("Downloads");
				transfers.addNode(downloads);
		
		tree.addNode(servers);
		tree.addNode(transfers);
		
		tree.setExpanded(true);
		servers.setExpanded(true);
	}
	
	int nodeCtr = -1;
	
	public void paintComponent(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawNode(tree, 0, g);
		
		nodeCtr = -1;
	}
	
	public void drawNode(Node n, int level, Graphics g){	
		
		g.setColor(Color.white);
		
		if(n.equals(selectedNode)){
			g.setColor(new Color(200,200,255));
		}else if(flashOn){
			g.setColor(flashColor);
		}
		
		
		if(level != 0){
			
			Rect nodeRect = nodeRect(nodeCtr,level);
			
			fillRect(nodeRect,g);
			
			g.setColor(Color.black);
			drawRect(nodeRect,g);	
			drawString(n.getName(),nodeRect,g);
		}
		
		if(n.size() != 0){						
			drawExpand(expandRect(nodeCtr,level),g,n.isExpanded());			
		}
		
		nodeCtr++;
		if(n.isExpanded()){
			for(int c = 0; c < n.size(); c++){
				drawNode(n.getNode(c),level+1,g);		
			}
		}
	}
	
	void drawExpand(Rect r, Graphics g, boolean expanded){
		
		g.setColor(Color.black);		
		g.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
		
		g.setColor(Color.white);
		
		//minus
		g.drawLine(r.getX() + 1, r.getY()  + r.getHeight()/2, r.getX() + r.getWidth() - 2, r.getY()  + r.getHeight()/2);
		
		if(!expanded){
			//plus
			g.drawLine(r.getX() + r.getWidth()/2, r.getY() + 1, r.getX() + r.getWidth()/2, r.getY() + r.getHeight() - 2);
		}
	
	}
	
	void drawString(String s, Rect r, Graphics g){
		
		FontMetrics fm = g.getFontMetrics();
		
		int yy = r.getHeight()/2 + r.getY() - (fm.getAscent() + fm.getDescent())/2 + fm.getAscent();
		
		g.drawString(s, r.getX() + 5, yy);
	}
	
	void fillRect(Rect r, Graphics g){
		g.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}
	
	void drawRect(Rect r, Graphics g){
		g.drawRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}
	
	////FROM HERE/////
	public void addServer(Server s){
		
		ServerNode server = new ServerNode(s);
		servers.addNode(server);
		
		//Set server as selected
		selectedNode = server;
		
		Client.getClient().getFrame().setRightPanel(s.getPanel());
		
		repaint();
	}
	
	public void updateServerName(Server s){
		
		Node n;
		if((n = servers.getNodeByObject(s)) != null){
			n.setName(s.getName());
			repaint();
		}
	}
	
	public void removeServer(Server s){
		servers.removeNode(servers.getNodeByObject(s));
		repaint();
	}
	
	public void addUser(User u, Server s){
		Node userNode = new Node(u.getName(), u);
		
		((ServerNode)servers.getNodeByObject(s)).getUserNode().addNode(userNode);
		repaint();
	}
	
	public void removeUser(User u, Server s){
		Node users = ((ServerNode)servers.getNodeByObject(s)).getUserNode();
		
		for(int c = 0; c < users.size(); c++){
			if(users.getNode(c).getNodeObject().equals(u)){
				users.removeNode(users.getNode(c));
				repaint();
			}
		}
	}
	
	public void updateUserName(User user, Server server){
		Node u;
		Node n;
		
		if((n = servers.getNodeByObject(server)) != null){
			if((u = n.getNodeByObject(user)) != null){
				u.setName(user.getName());
				repaint();
			}
		}
	}
	
	///////TO HERE//////
	
	public Rect nodeRect(int node, int level){
		return new Rect(level*levelSpacing, node*nodeHeight, getWidth() - level*levelSpacing - 1, nodeHeight);
	}
	
	public Rect expandRect(int node, int level){
		return new Rect((level-1)*levelSpacing + (levelSpacing)/2 - boxSize/2, node*nodeHeight  + nodeHeight/2 -boxSize/2, boxSize,boxSize);
	}
	
	public void nodeLeftClicked(Node n){
		JComponent pan = n.getPanel();
		 
		if(pan == null){
			pan = new JPanel(new BorderLayout());
			 
			JLabel lb = new JLabel(n.getName());
			lb.setHorizontalAlignment(SwingConstants.CENTER);
			 
			pan.add(lb,BorderLayout.CENTER);
		}
		
		selectedNode = n;
		Client.getClient().getFrame().setRightPanel(pan);
	}
	
	public void nodeRightClicked(Node n, int x, int y){
		
		Object data = n.getNodeObject();
		
		if(data instanceof Server){
			serverPopupMenu.setServer((Server)data);
			serverPopupMenu.show(this, x, y);
		}
		
		System.out.println("Right Clicked " + n.getName());
	}
	
	private class ServerPopupMenu extends JPopupMenu{
		
		Server s; 
		
		public ServerPopupMenu(){
			JMenuItem disconnect = new JMenuItem(new DisconnectAction());
			add(disconnect);
		}
		
		public void setServer(Server s){
			this.s = s;
		}
		
		class DisconnectAction extends AbstractAction{
			
			public DisconnectAction(){
				super("Disconnect");
			}
			
			public void actionPerformed(ActionEvent e) {
				Server currentServer = Client.getClient().getServerManager().getCurrentServer();
				
				if(currentServer != null){
					Client.getClient().getServerManager().removeServer(currentServer);
				}
			}
		}
	}
	
	class FlashTimer implements Runnable{
		
		boolean running = true;

		@Override
		public void run() {
			
			long start = System.currentTimeMillis();
			long goalTime = start + flashTime;
			
			while(running){
				
				if(System.currentTimeMillis() >= goalTime ){
					flashOn = !flashOn;
					goalTime += flashTime;
					repaint();
				}
				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void stop(){
			running = false;
		}
		
	}
	
	class Listener implements MouseListener, MouseMotionListener{

		int button = 0;
		
		public void mouseDragged(MouseEvent e) {
			mousePressed(e);
		}
		
		public void mousePressed(MouseEvent e) {
			
			//button
			int b = e.getButton();
			if(b != 0){
				button = b;
			}
			
			int num = e.getY()/nodeHeight+1;
			Node clickedNode = tree.getVisibleNode(num);
			
			//Null is no node was clicked on. 
			if(clickedNode == null){
				return;
			}
			
			int level = clickedNode.getLevel();
			Rect nodeRect = nodeRect(num-1,level);			
			Rect expandRect = expandRect(num-1, level);
			
			if(nodeRect.contains(e.getX(), e.getY())){
				if(button == 1){
					nodeLeftClicked(clickedNode);
				}else if(button == 3){
					nodeRightClicked(clickedNode,e.getX(),e.getY());
				}		
			}else if(expandRect.contains(e.getX(),e.getY())){
				clickedNode.toggleExpanded();
			}else{
				return;
			}
			
			repaint();			
		}
		
		public void mouseMoved(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
}