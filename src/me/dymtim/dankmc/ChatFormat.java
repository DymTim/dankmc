package me.dymtim.dankmc;

public enum ChatFormat {
    DANKMC("§c§lDANKMC §7➠ ", "§7", "§c", "§4");

    private String prefix, normal, info, error;

    ChatFormat(String prefix, String normal, String info, String error) {
        this.prefix = prefix;
        this.normal = "§r" + normal;
        this.info = "§r" + info;
        this.error = "§r" + error;
    }

    public String format(String s) {
        s = this.normal + s;
        s = this.prefix + s;
        s = s.replaceAll("&n", this.normal);
        s = s.replaceAll("&i", this.info);
        s = s.replaceAll("&e", this.error);
        return s;
    }

    public String formatNoPrefix(String s) {
        s = this.normal + s;
        s = s.replaceAll("&n", this.normal);
        s = s.replaceAll("&i", this.info);
        s = s.replaceAll("&e", this.error);
        return s;
    }

    public String format(String s, String p) {
        s = format(s);
        s = s.replaceAll("&p", p);
        return s;
    }
}
