package it.polimi.ingsw.view.cli;

import java.util.ArrayList;

public class Menù {
    private ArrayList<String> options;
    private final int menùLength;
    private final String padding = "  ";
    private String context = "";

    public Menù(ArrayList<String> options){
        this(options,80);
    }

    public Menù(ArrayList<String> options,int length){
        this.options = new ArrayList<String>(options);
        this.menùLength = length;
    }

    public void removeOption(String s) throws MenùException {
        String toRemove = this.options.stream().filter(x->x.equals(s)).findFirst().orElseThrow(MenùException::new);
        this.options.remove(toRemove);
    }

    public void addOption(String s){
        this.options.add(s);
    }

    public void print(){
        for(int i = 0;i<menùLength;i++){
            System.out.print("#");
        }
        System.out.print("\n");
        int menùIndex = 0;
        int counter;
        String header = "# ";
        for(counter =0; counter<this.context.length();counter++){
            if (counter == (menùLength-(header.length()+2+padding.length()))){
                System.out.print("   #");
                System.out.print("\n");
            }
            System.out.print(this.context.toCharArray()[counter]);
        }
        counter = counter%this.menùLength;
        while (counter<this.menùLength-(header.length()+2+padding.length())){
            System.out.print(" ");
        }
        System.out.print("   #");
        System.out.print("\n");
        for(String option : options){
            menùIndex ++;
            header = "# "+menùIndex+")"+padding;
            System.out.print(header);
            for(counter =0; counter<option.length();counter++){
                if (counter == (menùLength-(header.length()+2+padding.length()))){
                    System.out.print("   #");
                    System.out.print("\n");
                }
                System.out.print(option.toCharArray()[counter]);
            }
            counter = counter%this.menùLength;
            while (counter<this.menùLength-(header.length()+2+padding.length())){
                System.out.print(" ");
            }
            System.out.print("   #");
            System.out.print("\n");
        }
        for(int i = 0;i<this.menùLength;i++){
            System.out.print("#");
        }
        System.out.print("\n");
    }

    public static void main(String args[]){
        ArrayList<String> str = new ArrayList<>();
        str.add("opt1");
        str.add("opt2");
    }

    public void setContext(String s){
        this.context = s;
    }
}
