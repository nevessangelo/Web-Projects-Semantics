/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbpediaspotlight;

/**
 *
 * @author angelo
 */
public class DBPediaSpotlight {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String question = "Semantic Web";
        db c = new db();
        c.configiration(0.0, 0, "non", "CoOccurrenceBasedSelector", "Default", "yes");
        c.evaluate(question);
        System.out.println("resource : " + c.getResu());
    }

}
