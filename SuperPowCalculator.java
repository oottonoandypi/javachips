package javachips;

public class SuperPowCalculator {
	// calculate ab mod 1337 where a is a positive integer 
	// and b is an extremely large positive integer given in the form of an array.
	
	// Cleanup: generic solution that can be used more than just 1337
	public static int superPow(int a, int[] b, int modBy) {
        int[] mods=new int[10];
        mods[0]=1;
        mods[1]=a%modBy;
        for(int i=2; i<10; i++){
            mods[i]=mods[i/2]*mods[i-i/2]%modBy;
        }
        
        int mod=1;
        // HashMap<Integer, int[]> mods=new HashMap<Integer, int[]>();
        for(int bNum: b){
            mod=(int)modOfPow(mod, 10, modBy)*mods[bNum]%modBy;
            // mod=(int)modOfPow_1337(mod, 10, mods)*modOfPow_1337(a, bNum, mods)%1337;
           //mod=Math.pow(Math.pow(mod,5)%1337,2)%1337*mods[bNum]%1337;
        }
        
        return mod;
    }
	private static int modOfPow(int mod, int power, int modBy){
        // if(power==0) return 1;
        if(power==1) return (int)mod%modBy;
        int halfMod=(int)modOfPow(mod, power/2, modBy);
        
        if(power%2==0) return halfMod*halfMod%modBy;
        return (halfMod*halfMod%modBy)*(mod%modBy)%modBy;
        // return (int)(modOfPow_1337(mod, power/2)%1337 * modOfPow_1337(mod, power-power/2)%1337)%1337;
    }
	
	// -----------------------------------------------------------------------------------------------
	// 2nd solution: make Math.pow(Math.pow(mod,5)%1337,2)%1337 more efficient
	// create a custom pow method that takes lgn runtime
	// then replace Math.pow with this custom pow mehthod
	public static int superPow_1337(int a, int[] b) {
        int[] mods=new int[10];
        mods[0]=1;
        mods[1]=a%1337;
        for(int i=2; i<10; i++){
            mods[i]=mods[i/2]*mods[i-i/2]%1337;
        }
        
        int mod=1;
        // HashMap<Integer, int[]> mods=new HashMap<Integer, int[]>();
        for(int bNum: b){
            mod=(int)modOfPow_1337(mod, 10)*mods[bNum]%1337;
            // mod=(int)modOfPow_1337(mod, 10, mods)*modOfPow_1337(a, bNum, mods)%1337;
           //mod=Math.pow(Math.pow(mod,5)%1337,2)%1337*mods[bNum]%1337;
        }
        
        return mod;
    }
	private static int modOfPow_1337(int mod, int power){
        // if(power==0) return 1;
        if(power==1) return (int)mod%1337;
        int halfMod=(int)modOfPow_1337(mod, power/2);
        
        if(power%2==0) return halfMod*halfMod%1337;
        return (halfMod*halfMod%1337)*(mod%1337)%1337;
        // return (int)(modOfPow_1337(mod, power/2)%1337 * modOfPow_1337(mod, power-power/2)%1337)%1337;
    }
	
	// -----------------------------------------------------------------------------------------------
	// Thought process, 1st attempt：
	// a^b=a^(k)*a^(b-k) => 
	// a^b=(a^(b/10))^10
	public static int superPow_1stAttempt(int a, int[] b) {
        int[] mods=new int[10];
        mods[0]=1;
        mods[1]=a%1337;
        // System.out.println(Math.pow(Math.pow(911,5)%1337,2)%1337);
        for(int i=2; i<10; i++){
            mods[i]=mods[i/2]*mods[i-i/2]%1337;
             // System.out.println(mods[i]);
        }
        
        double mod=1;
        for(int bNum: b){
            // System.out.println(Math.pow(mod, 10));
            // System.out.println(Math.pow(mod, 10)%1337);
            // System.out.println((Math.pow(mod, 10)%1337*mods[bNum])%1337);
            mod=Math.pow(Math.pow(mod,5)%1337,2)%1337*mods[bNum]%1337;
            // System.out.println(mod);
        }
        
        return (int)mod;
    }
	
}
