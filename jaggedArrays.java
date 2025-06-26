class jaggedArrays{
    public static void main(String[] args) {
        
        int [] [] arr = new int  [3] []; // jagged

        arr[0] = new int[5];
        arr[1] = new int[2];
        arr[2] = new int[6];

        for(int i=0 ; i<arr.length; i++){            // Filling the jagged array with random numbers
            for(int j=0;j<arr[i].length;j++){

                arr[i][j] = (int)(Math.random() *10);


            }
        }
        for(int i=0 ; i<arr.length; i++){           //Printing the jagged array.
            for(int j=0;j<arr[i].length;j++){
                System.out.print(arr[i][j]+ " ");
            }
            System.out.println();
        }



    }
}