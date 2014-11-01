package annotator;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by penguin on 30/10/14.
 */

public class MutationCompare {

    public static void main(String[] args) {

        ArrayList<Integer> L1 = new ArrayList<Integer>();
        ArrayList<Integer> L2 = new ArrayList<Integer>();

        L1.add(1);
        L1.add(6);
        L1.add(23);
        L1.add(32);
        L1.add(56);
        L1.add(77);

        L2.add(3);
        L2.add(8);
        L2.add(23);
        L2.add(44);
        L2.add(54);
        L2.add(55);
        L2.add(81);
        L2.add(91);

        Iterator Lone = L1.iterator();
        Iterator Ltwo = L2.iterator();

//        int c = 0;
//
//        Integer one = (Integer) Lone.next();
//        Integer two = (Integer) Ltwo.next();
 //       c = 2+ c;
        //Lone.hasNext() || Ltwo.hasNext()

 //       int threshold = L1.size()+L2.size();

//        while(c<=threshold){
//
//
//            if(one == two){
//                // Merge Both Annotations into one
//                System.out.println(one + " has been merged");
//                // Then Take two
//                two = (Integer) Ltwo.next();
//                one = (Integer) Lone.next();
//                c++;
//                c++;
//            }
//            else if(one > two){
//                // Store the smallest page number tmVar into a new annotation.
//                // PROCESS current tmVar
//                System.out.println(two );
//                two = (Integer) Ltwo.next();
//                c++;
//            }
//            else if(one < two){
//                // Store the smallest page number seth into a new annotation.
//                // PROCESS current seth annotation
//                System.out.println(one);
//                one = (Integer) Lone.next();
//                c++;
//            }
//
//        }

        System.out.println(merge(L1, L2));





    }

    public static ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b) {

        ArrayList<Integer> answer = new ArrayList<Integer>();
        int i = 0, j = 0, k = -1;

        while (i < a.size() && j < b.size()) {
            if (a.get(i) == b.get(j)) {
                answer.add(a.get(i++));
                j++;

            } else {
                if (a.get(i) < b.get(j))
                    answer.add(a.get(i++));
                else
                    answer.add(b.get(j++));
            }
        }

        while (i < a.size())
            answer.add(a.get(i++));


        while (j < b.size())
            answer.add( b.get(j++));

        return answer;
    }

}