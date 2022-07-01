package it.polimi.ingsw.view.cli;

import java.util.ArrayList;

/**
 * @author Davide Grazzani
 * Class that represents the menu for the game
 */
public class Menù {
    private final ArrayList<String> options;
    private final int menùLength;
    private final String padding = "  ";
    private String context = "";

    /**
     * Class constructor
     * @param options represents the possible options a player can make
     */
    public Menù(ArrayList<String> options){
        this(options,80);
    }

    /**
     * Class constructor
     * @param options represents the possible options a player can make
     * @param length represents the length of the menu
     */
    public Menù(ArrayList<String> options,int length){
        this.options = new ArrayList<String>(options);
        this.menùLength = length;
    }

    /**
     * Method that removes an option from the available ones
     * @param s represents the option to remove
     * @throws MenùException launched if the specified option isn't correct or presents in the list of available options
     */
    public void removeOption(String s) throws MenùException {
        String toRemove = this.options.stream().filter(x->x.equals(s)).findFirst().orElseThrow(MenùException::new);
        this.options.remove(toRemove);
    }

    /**
     * Method that adds an option to the available ones
     * @param s represents the option to add
     */
    public void addOption(String s){
        this.options.add(s);
    }

    /**
     * Method that prints the menu
     */
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
        System.out.print("Your choice : ");
    }

    /**
     * setter method
     * @param s represents the context to set. A context is the
     */
    public void setContext(String s){
        this.context = s;
    }

    /**
     * Method that clears the menu
     */
    public void clear(){
        this.options.clear();
    }

    /**
     * Method that adds more options to the available ones
     * @param options represents the arrayList of options to add
     */
    public void addOptions(ArrayList<String> options){
        for(String option : options){
            this.addOption(option);
        }
    }

    /**
     * method that prints
     * @param s represents the
     * @param optNumber represents the number of the selected option (?)
     */
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
