public class Room {

    private byte roomId;

    private Room next;
    private Room previous;
    private Room left;
    private Room right;

    public String name;

    public Room(String text, byte roomId) {
        this.name = text;
        this.roomId = roomId;
    }

    public void printName(){
        System.out.println(this.name);
        around();
        System.out.println("\n");
    }

    public byte getId(){
        return this.roomId;
    }

    public Room next(){
        return this.next;
    }

    public Room left(){
        return this.left;
    }

    public Room right(){
        return this.right;
    }

    public Room previous(){
        return this.previous;
    }

    public void createNext(String text, byte roomId){
        Room next = new Room(text, roomId);
        this.next = next;
        next.previous = this;
    }

    public void createLeft(String text, byte roomId){
        Room left = new Room(text, roomId);
        this.left = left;
        left.previous = this;
    }

    public void createRight(String text, byte roomId){
        Room right = new Room(text, roomId);
        this.right = right;
        right.previous = this;
    }

    public void around(){
        if(this.next() == null && this.previous() == null && this.left() == null && this.right() == null){
            System.out.println("Вокруг ничего нет");
            return;
        }

        if(this.next() != null){
            System.out.println("Спереди : " + this.next().name);
        }
        if(this.previous() != null){
            System.out.println("Сзади : " + this.previous().name);
        }
        if(this.left() != null){
            System.out.println("Слева : " + this.left().name);
        }
        if(this.right() != null){
            System.out.println("Справа : " + this.right().name);
        }
    }

    /*
    public void delite(){
        byte roomId = this.roomId;

    }
    */
}
