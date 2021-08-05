public class event {

    // Variables:
    public String name = "N/A"; // Name of Event
    public String day = "N/A"; // What day the event occurs
    public String info = "N/A"; // notes for the event

    public int startTime = 0; // Enter in 24hr format, start of the event
    public int endTime = getEndTime();
    public double length = 0; // How long is this event
    
    // Default Constructor
    public event(){
        // Default Constructor
    }

    // Default Constructor
    public event(String n, String d, double l){
        this.name = n;
        this.day = d;
        this.length = l;
    }

    // Default Constructor
    public event(String n, String d, int t, double l){
        this.name = n;
        this.day = d;
        this.startTime = t;
        this.length = l;
    }

    // Returns the day of event
    public String getDay(){
        char x = this.day.charAt(0);
        if (x == 'm' || x == 'M')
            this.day = "Monday";
        else if (this.day.charAt(1) == 'u' || this.day.charAt(1) == 'U')
            this.day = "Tuesday"; 
        else if (x == 'w' || x == 'W')
            this.day = "Wednesday"; 
        else if (this.day.charAt(1) == 'h' || this.day.charAt(1) == 'H')
            this.day = "Thursday"; 
        else if (x == 'f' || x == 'F')
            this.day = "Friday"; 
        return this.day;
    }

    // Sets event day
    public void setDay(String day){
        this.day = day;
    }

    // Sets name of the event
    public void setName(String name){
        this.name = name;
    }

    // Returns the name of the event
    public String getName(){
        return this.name;
    }

    // Returns the time the event ends in 24hr format
    public String endTimeFormat(){

        if (this.getEndingMinute() < 6)
            return this.getEndingHour()+":"+this.getEndingMinute()+"0";
        else
            return this.getEndingHour()+":"+this.getEndingMinute();            
    }

    // Returns the start time in 24hr format
    public String startTimeFormat(){
        if (this.getStartingMinute() < 6)
            return this.getStartingHour()+":"+this.getStartingMinute()+"0";
        else
            return this.getStartingHour()+":"+this.getStartingMinute();  
    }

    // Returns full ending time
    public int getEndTime(){

        int t = this.startTime;
        int t1 = this.startTime;
        double m = 0;
        double l = this.length;

        t += Math.floor(l)*100 + (l - Math.floor(l))*60;
        t1 = t;

        // Find the minutes
        for (int i=0; i<2; i++){
            int a = t%10;
            t /= 10;
            m += a*Math.pow(10, i);
        }

        // If the minutes + the hour exceeds an hour, then increase the time
        if (m >= 60){
            t1 += 100 - m;
            t1 += (m-60);
        }

        return t1;
    }

    // Returns the minutes in the end time
    public int getEndingMinute(){
        int t = getEndTime();
        double m = 0;
        for (int i=0; i<2; i++){
            int a = t%10;
            t /= 10;
            m += a*Math.pow(10, i);
        }

        return (int)m;
    }

    // Returns the minutes in the start time
    public int getStartingMinute(){
        int t = this.startTime;
        double m = 0;
        for (int i=0; i<2; i++){
            int a = t%10;
            t /= 10;
            m += a*Math.pow(10, i);
        }

        return (int)m;
    }

    // Returns the hour in the start time
    public int getStartingHour(){
        int time = this.startTime;
        return (time/100); 
    }
    
    // Returns the hour in the end time
    public int getEndingHour(){
        int time = getEndTime();
        return (time/100);
    }

    // Sets the time of the event
    public void setTime(int t){
        this.startTime = t;
    }

    // Returns full starting time
    public int getStartTime(){
        return this.startTime;
    }

    // Allows user to add notes
    public void addNotes(String info){
        this.info = info;
    }

    // Returns notes added on by the user
    public String getNotes(){
        return info;
    }

    // Sets the length of the event
    public void setLength(double l){
        this.length = l;
    }

    // Returns the length of the event
    public double getLength(){
        return this.length;
    }

    // Returns event information
    public String toString(){
        String x = getDay();
        return "\nEvent: "+this.name+"\nDay: "+x+" @ "+this.startTimeFormat()+"\nEnds: "+this.endTimeFormat()+"\nExtra Notes: "+info;
    }
 }
