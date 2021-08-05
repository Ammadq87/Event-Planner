import java.util.Stack;
import javax.swing.JOptionPane;

public class eventRunner {
    
    Stack <event> Events = new Stack<event>();
    event events[] = new event[50];
    int amtOfEvents = 0;

    public static void main(String []args) {
        new eventRunner();
    }

    public void printDay(Stack<event> day){
        
        Stack<event> day1 = new Stack<event>();

		while(!day.isEmpty()){
			int t = findMax(0, 0, day);
                    
            for (int i=0; i<day.size(); i++){
                if (day.get(i).getStartTime() == t){
			        day1.push(day.get(i));
                    day.remove(i);
                    i = day.size();
                }
            }
        }

        for (int i=day1.size()-1; i>-1; i--){
            System.out.println(day1.get(i).toString()+"\n<===========>");
        }
    }

    public boolean validDay(String d){
        String day[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i=0; i<day.length; i++){
            if (d.compareTo(day[i]) == 0){
                return true;
            }
        }

        return false;
    }

    public void printSchedule(){

        if (amtOfEvents > 0){
			String d = JOptionPane.showInputDialog("Enter day of week:");
            if (d == null)
                JOptionPane.showMessageDialog(null,"Required Field Not Filled(s)","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
            else{
                if (validDay(d)){
                    Stack <event> day = thing(d);
                    System.out.println("<----------- "+d+" Events ----------->");
                    printDay(day);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Invalid Day Entered","View Event",JOptionPane.ERROR_MESSAGE);
                }  
            }
		}

        else{
            JOptionPane.showMessageDialog(null,"No Events Added","View Event",JOptionPane.ERROR_MESSAGE);
        }
    }

	public int findMax(int max, int i, Stack <event> s){
        if (i < s.size()){
            if (max > s.get(i).getStartTime())
                return findMax(max, i+1, s);
            else
                return findMax(s.get(i).getStartTime(), i+1, s);
        }
        else
            return max;

    }

	public Stack<event> thing(String day){
		Stack <event> s = new Stack <event>();
		for (int i=0; i<Events.size(); i++){
			if (Events.get(i).getDay().compareTo(day) == 0){
				s.push(Events.get(i));
			}
		}
		return s;
	} 

    public void eventRunnerConsole(){
        String[] options = {"Add Event", "Edit Event", "View Event", "Delete Event", "Print Events"};
        int x = JOptionPane.showOptionDialog(null, "What Would You Like to do?",
                "Schedule.Builder",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        while (x > -1){
            if (x == 0){
                createEvent();
            }

            else if (x == 1){
                editEvent();
            }

            else if (x == 2){
                viewEvent();
            }

            else if (x == 3){
				deleteEvent();
            }

            else{
                printSchedule();
            }

            x = JOptionPane.showOptionDialog(null, "What Would You Like to do?",
            "Schedule.Builder",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }
    }

    public eventRunner(){

        // init();
        eventRunnerConsole();
       
    }

	public void deleteEvent(){
		if (Events.size() > 0){
			String name = JOptionPane.showInputDialog("Enter Event Name:");

            if (name == null)
                JOptionPane.showMessageDialog(null,"Required Field Not Filled(s)","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
            else{
                int index = getIndexOfEvent(name);
                if (index == -1){
                    JOptionPane.showMessageDialog(null,"EVENT NOT FOUND!","Event Edit",JOptionPane.ERROR_MESSAGE);
                }

                else{
                    JOptionPane.showMessageDialog(null,"Event \""+Events.get(index).getName()+"\" deleted", "View Event",JOptionPane.INFORMATION_MESSAGE);
            	    Events.remove(index);
				    amtOfEvents--;
			    }
            }  
		}
		else{
            JOptionPane.showMessageDialog(null,"No Events Added","View Event",JOptionPane.ERROR_MESSAGE);
        }
	}

    public void viewEvent(){
        
        if (Events.size() > 0){
            String name = JOptionPane.showInputDialog("Enter Event Name:");
            if (name == null)
                JOptionPane.showMessageDialog(null,"Required Field Not Filled(s)","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
            else{
                int index = getIndexOfEvent(name);
                if (index == -1){
                    JOptionPane.showMessageDialog(null,"EVENT NOT FOUND!","Event Edit",JOptionPane.ERROR_MESSAGE);
                }

                else{
                    event temp = Events.get(index);
                    JOptionPane.showMessageDialog(null,"Event Name: "+temp.getName()+"\nEvent Date: "+temp.startTimeFormat()+
                    "\nEvent Length: "+temp.getLength()+"\nEvent End: "+temp.endTimeFormat()+"\nNotes: "+temp.getNotes(),
                    "View Event",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"No Events Added","View Event",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editEvent(){        
        String name = JOptionPane.showInputDialog("Enter Event Name:");

        if (name == null)
            JOptionPane.showMessageDialog(null,"Required Field Not Filled(s)","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
        else{
            int index = getIndexOfEvent(name);
            if (index == -1){
                JOptionPane.showMessageDialog(null,"EVENT NOT FOUND!","Event Edit",JOptionPane.ERROR_MESSAGE);
            }
            else{
                editMenu(Events.get(index));
            }
        }    
    }

    public void editMenu(event e){

        String[] options = {"Name", "Day", "Time", "Length"};
        int x = JOptionPane.showOptionDialog(null, "What Would You Like to Change about "+e.getName()+"?",
                "Edit Event",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (x == 0){
            String n = JOptionPane.showInputDialog("Event Name:");
                e.setName(n);
                JOptionPane.showMessageDialog(null,"Event Name Changed!","Event Edit",JOptionPane.PLAIN_MESSAGE);
        }

        else if (x == 1){
            String d = JOptionPane.showInputDialog("Event Day:");
                JOptionPane.showMessageDialog(null,"Required Field Not Filled(s)","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
                e.setDay(d);
                JOptionPane.showMessageDialog(null,"Event Day Changed!","Event Edit",JOptionPane.PLAIN_MESSAGE);
        }
    
        else if (x == 2){
            // Set a temp variable to hold previous time - incase there are conflicts with the time
            int tempTime = e.getStartTime();

            // Prompt User for Input
            String time = JOptionPane.showInputDialog("Event Time:");
                int t = Integer.parseInt(time);
                e.setTime(t);

                // Check if event exists
                int index = getIndexOfEvent(e.getName());
                if (index == -1){
                    JOptionPane.showMessageDialog(null,"EVENT NOT FOUND!","Event Edit",JOptionPane.ERROR_MESSAGE);
                }

                else{
                    // If event exists, check if there are any collisions with other events
                    event temp = collisions(index);
                    if (temp != null){
                        JOptionPane.showMessageDialog(null,"Event \""+Events.get(amtOfEvents).name+"\" conflicts with Event \""+temp.name+"\"","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
                        e.setTime(tempTime);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Event Start Time Changed!","Event Edit",JOptionPane.PLAIN_MESSAGE); 
                }
        }
    
        else if (x == 3){
            String length = JOptionPane.showInputDialog("Event Length:");
                double l = Integer.parseInt(length);
                e.setLength(l);

                double tempLength = e.getLength();
                // Check if event exists
                int index = getIndexOfEvent(e.getName());
                if (index == -1){
                    JOptionPane.showMessageDialog(null,"EVENT NOT FOUND!","Event Edit",JOptionPane.ERROR_MESSAGE);
                }

                else{
                    // If event exists, check if there are any collisions with other events
                    event temp = collisions(index);
                    if (temp != null){
                        JOptionPane.showMessageDialog(null,"Event \""+Events.get(amtOfEvents).name+"\" conflicts with Event \""+temp.name+"\"","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
                        e.setLength(tempLength);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Event Start Length Changed!","Event Edit",JOptionPane.PLAIN_MESSAGE); 
                }
            }
    }

    public int getIndexOfEvent(String name){
        for (int i=0; i<Events.size(); i++){
            if (Events.get(i).getName().compareTo(name) == 0){
                return i;
            }
        }
        return -1;
    }

    public void createEvent(){
        String name = JOptionPane.showInputDialog("Event Name:");
        String day = JOptionPane.showInputDialog("Event Day:");
        String t = JOptionPane.showInputDialog("Event Start Time:");
        String l = JOptionPane.showInputDialog("Event Length:");

        if (name == null || day == null || t == null || l == null){
            JOptionPane.showMessageDialog(null,"Required Field Not Filled(s)","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
        }

        else{

            int time = Integer.parseInt(t);
            double length = Double.parseDouble(l);
            
            event e = new event(name, day, time, length);
            Events.push(e);
            event collision = collisions(Events.size());
            
            if (collision == null){
                amtOfEvents++;
            }
            else{
                JOptionPane.showMessageDialog(null,"Event \""+Events.get(amtOfEvents).getName()+"\" conflicts with Event \""+collision.name+"\"","Error: Collision of Events",JOptionPane.ERROR_MESSAGE);
                Events.remove(amtOfEvents);
            }
        }
    }

    public event collisions(int index){
        
        if (amtOfEvents >= 1){

            for (int i=0; i<amtOfEvents; i++){
            // Land on the same day
                if (i != index && Events.get(i).getDay().compareTo(Events.get(index-1).getDay()) == 0){
                // events[index] occurs before events[i]
                    if (Events.get(index-1).getStartTime() < Events.get(i).getStartTime() && Events.get(index-1).getEndTime() <= Events.get(i).getStartTime()){
                        // No collisions
                        return null;
                    }
            
                    else if (Events.get(index-1).getStartTime() >= Events.get(i).getEndTime()){
                        // No collisions
                        return null;
                    }

                    else{
                        return Events.get(index-1);
                    }
                }

                // Same Name
                else if (i != index && Events.get(i).getName().compareTo(Events.get(index-1).getName()) == 0){
                    return Events.get(index-1);
                }
            }
        }
        return null;
    }
}
