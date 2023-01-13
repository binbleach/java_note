package HotelMgtSystem;

public class Rooms {
    private int roomid;
    private String type;
    private boolean state;

    public Rooms(int roomid, String type, boolean state) {
        this.roomid = roomid;
        this.type = type;
        this.state = state;
    }

    public Rooms() {
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        Rooms a = (Rooms)obj;
        if(this.roomid==a.getRoomid())return true;
        return false;
    }

    @Override
    public String toString() {
        return roomid+type+(state?"空闲":"占有");
    }
}
