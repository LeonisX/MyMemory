package md.leonis.MyMemory;

// Еще можно добавить таймер с прямым или обратным отсчетом сколько секунд потрачено на запоминание
// перерасчёт фигурок при изменении размера (для фазы-2)
// вести статистику попаданий


import java.awt.EventQueue;
import java.util.prefs.BackingStoreException;

public class MyMemory {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length>0){
			try{
				Prefs.myPrefs.removeNode();
			} catch (BackingStoreException e){
				System.out.println("Не очистить реестр после себя!!!");
			}
		} 

	    
	    
	    
		new MyMemory();
	}
	
	MyMemory(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prefs.mainFrame.setVisible(true);
					if(Prefs.phase==1) Prefs.action0();
						else Prefs.action10();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}