import java.io.*;

public class LevelFile extends File {

    private byte id;

    public LevelFile(String pathname) {
        super(pathname);
    }

    public byte getId() {
        return id;
    }

    private void existsNew() throws IOException {
        if(!this.exists()){
            this.createNewFile();
        }
    }

    private boolean existsError() throws IOException {
        if(!this.exists()){
            System.out.println("Файла не существует");
        }
        return true;
    }
    
    public void write(String text) throws IOException {
        this.existsNew();

        PrintWriter out = new PrintWriter(this.getAbsoluteFile());

        out.print(text);
        out.close();
    }

    public void update(String text) throws IOException {
        this.existsNew();
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        string = read();
        stringBuilder.append(string);
        stringBuilder.append("\n");
        write(stringBuilder.toString() + text);
    }



    public String read() throws IOException {
        if(!this.existsError()){
            return null;
        }

        StringBuilder stringB = new StringBuilder();

        BufferedReader in = new BufferedReader(new FileReader(this.getAbsoluteFile()));
        String string;

        while ((string = in.readLine()) != null){
            stringB.append(string);
            stringB.append("\n");
        }

        in.close();

        return stringB.toString();
    }

    public void saveLevel(NewRoom room, byte id) throws IOException {
        NewRoom newRoom =  room;
        StringBuilder stringBuilder = new StringBuilder();
        write(String.valueOf(id) + "\n");

        do {
            stringBuilder.append(newRoom.getQuantity());
            stringBuilder.append("\n");
            stringBuilder.append(newRoom.getText());
            stringBuilder.append("\n\n");
            newRoom = newRoom.getBackDoorNext();
        }while (newRoom != null);

        update(stringBuilder.toString());
    }

    public NewRoom loadLevel() throws IOException {
        if(read() == null){
            System.out.println("Файл не найден или пуст. Создана новая карта");
            NewRoom newRoom = new NewRoom((byte) 0, 2);
            return newRoom;
        }

        BufferedReader in = new BufferedReader(new FileReader(this.getAbsoluteFile()));

        id = Byte.parseByte(in.readLine());
        in.readLine();
        NewRoom newRoom = new NewRoom((byte) 0, Integer.parseInt(in.readLine()));
        newRoom.setText(in.readLine());
        in.readLine();

        for(byte i = 1; i < id; i++){
            newRoom.createNew(i, Integer.parseInt(in.readLine()));
            newRoom = newRoom.getBackDoorNext();
            newRoom.setText(in.readLine());
            in.readLine();
        }

        return newRoom;

    }

}
