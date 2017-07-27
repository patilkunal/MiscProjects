import java.util.*; 

class WeakHashMapExample implements Runnable { 

    static WeakHashMap map = new WeakHashMap(); 
    static Integer key = new Integer(123); 

    public static void main(String[] args) { 

        map.put(key, null); 
        new Thread(new WeakHashMapExample()).start(); 

        try { 
            System.in.read(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 

        key = null; 
    } 

    public void run() { 

        Integer keyCopy = new Integer(123); 

        while (map.containsKey(keyCopy)) { 
            try { 
                Thread.sleep(1000); 
                System.out.println("Still Here"); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            System.gc(); 
        } 
        System.out.println("Key will be garbage collected, bye"); 
    } 
} 

