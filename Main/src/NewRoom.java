public class NewRoom {

    private byte id;

    private NewRoom backDoorNext;
    private NewRoom backDoorPriveous;

    private NewRoom tonel[];

    private int quantity;

    private String text;

    public NewRoom(byte id, int quantity){
        this.tonel = new NewRoom[quantity];
        this.quantity = quantity;
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public void setTonel(NewRoom room, int slot){
        this.tonel[slot] = room;
    }
    
    public byte getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public NewRoom getBackDoorNext(){
        return backDoorNext;
    }

    public NewRoom getBackDoorPriveous() {
        return backDoorPriveous;
    }
    
    public void createNew(byte id, int quantity){
        NewRoom next = new NewRoom(id, quantity);
        backDoorNext = next;
        next.backDoorPriveous = this;
    }
    
    public void around(){
        boolean j = false;
        System.out.println("Вокруг :");
        for(byte i = 0; i < quantity; i++){
            if (this.tonel[i] != null) {
                j = true;
                System.out.println("комната " + this.tonel[i].getId() + "в тонеле " + i);
            }
        }
        if(!j){
            System.out.println("ничего нет");
        }
    }
    
    public NewRoom go(int slot){
        return this.tonel[slot];
    }
}

