package client.ui;

import java.awt.Color;

public class Theme {

	Color background;
	Color text;
	
	public Theme(Color background, Color text){
		this.background = background;
		this.text = text;
	}
	
	public Color getBackgroundColor(){
		return background;
	}
	
	public Color getTextColor(){
		return text;
	}
}