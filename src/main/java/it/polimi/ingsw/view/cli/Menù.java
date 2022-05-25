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
        if(!this.context.equals("")){
            this.println(this.context,0);
            for(int i = 0;i<menùLength;i++){
                System.out.print("#");
            }
            System.out.print("\n");
        }
        int menùIndex = 0;
        for(String option : options){
            menùIndex ++;
            this.println(option,menùIndex);
        }
        for(int i = 0;i<this.menùLength;i++){
            System.out.print("#");
        }
        System.out.print("\n");
    }

    public void setContext(String s){
        this.context = s;
    }

    public void clear(){
        this.options.clear();
    }

    public void addOptions(ArrayList<String> options){
        for(String option : options){
            this.addOption(option);
        }
    }

    private void println(String s,int optNumber){
        String header = "# ";
        String footer = " #";
        if(optNumber != 0){
            header += optNumber+") ";
        }
        System.out.print(header);
        int position = header.length();
        for(int counter = 0; counter<s.length(); counter++){
            for(position = header.length();position<(this.menùLength-footer.length());position++){
                if(counter<s.length()){
                    System.out.print(s.toCharArray()[counter]);
                    counter++;
                }else{
                    System.out.print(" ");
                }
            }
            header = "# ";
            System.out.print(footer+"\n");
            if(counter<s.length()){
                System.out.print(header);
            }
        }
    }
}
