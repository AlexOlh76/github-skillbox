import java.io.PrintWriter;

public class Loader{

    private static int fileNumber = 1;

    private int beginRegionNumber;
    private int endRegionNumber;

    public Loader(int beginRegionNumber, int endRegionNumber){

        this.beginRegionNumber = beginRegionNumber;
        this.endRegionNumber = endRegionNumber;
    }



    public void writer() throws Exception {

        String fileName = "res/number" + (fileNumber++) + ".txt";

        PrintWriter writer = new PrintWriter(fileName);

        char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

        for (int regionCode = beginRegionNumber; regionCode <= endRegionNumber; regionCode++) {
            StringBuilder builder = new StringBuilder();


            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append('\n');
                        }
                    }
                }
            }
            writer.write(builder.toString());
        }

        writer.flush();
        writer.close();

    }

    private static StringBuilder padNumber(int number, int numberLength) {
        StringBuilder builder = new StringBuilder();

        builder.append(number);
        int padSize = numberLength - builder.length();

        for (int i = 0; i < padSize; i++) {
            builder.insert(0, '0');
        }

        return builder;
    }
}
