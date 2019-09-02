public class Q8 {

    public Q8(){
        //print out sum of diagonals when 1001 by 1001
        //x represents the diagonal of the square
        int x = 1001;
        System.out.println("When X = " + x + ", Sum of diagonals of spiral = " + getTotalDiagonally(x));
    }


    /**
     * Finds the sum of diagonals by perfoming mathematical calculations on layer number up until the given x
     *
     * the spiral is views as a series of boxes within boxes.
     * each box with a width greater than one has a top left, top right, bottom left, and bottom right corner which
     * can be calculated. These were calculated by finding a formula after looking at corner in a 5 by 5, 7 by 7 and
     * a 9 by 9 box and looking for the trend.
     *
     *
     * @param X
     * @return total: this is the sum of diagonals
     */
    private Long getTotalDiagonally(int X){
        //layerNum is the layer of the box. this can be considered as the left hand corner of each box
        int layerNum = 2;
        //width is the width of the square.
        int width = 1;
        //start at 1 as center of square is one
        long total = 1;

        //Loop until width is greater than x
        while (width < X){
            //Top Left
            total += 4 * power(layerNum, 2) - 6 * layerNum + 3;
            //Top Right
            total += 4 * power(layerNum, 2) - 4 * layerNum + 1;
            //Bottom Right
            total += 4 * power(layerNum, 2) - 8 * layerNum + 5;
            //Bottom Left
            total += 4 * power(layerNum, 2) - 10 * layerNum + 7;

            //layer number is incremented as each new loop increments the layer
            layerNum += 1;
            //width is increased by two as a number is added to each side
            width += 2;
        }
        //return the sum
        return total;
    }

    /**
     * This is a reimplementation of the power library
     * method performs a power calculation with the base and power being passed in.
     *
     * @param base :the number that the power function is being applied to
     * @param power :  the power of the function
     * @return
     */
    private int power(int base, int power){
        int number = 1;
        if (power != 0){
            //loop from 0 to the power
            for (int i = 0; i < power; i++){
                //times number by the base
                number = number * base;
            }
        }
        return number;
    }



    public static void main(String[] args){
        //Run Q8
        new Q8();
    }
}
