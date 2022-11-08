public class ParametersBag {

    private String path;
    private long limit;

    public ParametersBag(String args[]) {
        if(args.length != 4) {
            throw new IllegalArgumentException(
                    "Неверное колличество аргументов. " +
                    "Формат: -d /путь к дериктории -l лимит");
        }
        if()
        path = args[1];
        limit = Long.valueOf(args[3]);
    }

    public long getLimit() {
        return limit;
    }

    public String getPath() {
        return path;
    }
}
