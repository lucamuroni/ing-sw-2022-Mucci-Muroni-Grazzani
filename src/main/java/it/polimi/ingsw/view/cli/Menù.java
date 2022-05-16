package it.polimi.ingsw.view.cli;

import java.util.ArrayList;

public class Menù {
    private ArrayList<String> options;
    private final int menùLength;
    private final String padding = "  ";

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
        for(String option : options){
            menùIndex ++;
            String header = "# "+menùIndex+")"+padding;
            System.out.print(header);
            int counter = 0;
            while (counter < (menùLength-(header.length()+2+padding.length())) && counter< option.length()){
                System.out.print(//pezzo di parola da scrivere);
            }
        }
    }
}
