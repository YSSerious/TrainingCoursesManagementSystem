
package ua.ukma.nc.mail;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Oleh Khomandiak on 3 ����. 2016 �.
 *
 */
public class Send {
	@Autowired
	private static
	EmailSender sender = new EmailSender();
	public static  void main (String[] args){
		
		
		sender.send("xoma02@gmail.com", "azaz", "azaz");
		
	}
}
