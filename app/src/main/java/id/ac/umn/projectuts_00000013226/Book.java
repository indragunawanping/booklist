package id.ac.umn.projectuts_00000013226;

public class Book {
    private String _asin, _group, _format, _title, _author, _publisher;
    private int _favorite;

    public Book(){}
    public Book(String _asin, String _group, String _format, String _title, String _author, String _publisher, int _favorite) {
        this._asin = _asin;
        this._group = _group;
        this._format = _format;
        this._title = _title;
        this._author = _author;
        this._publisher = _publisher;
        this._favorite = _favorite;
    }

    public String get_asin() {
        return _asin;
    }

    public void set_asin(String _asin) {
        this._asin = _asin;
    }

    public String get_group() {
        return _group;
    }

    public void set_group(String _group) {
        this._group = _group;
    }

    public String get_format() {
        return _format;
    }

    public void set_format(String _format) {
        this._format = _format;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }

    public String get_publisher() {
        return _publisher;
    }

    public void set_publisher(String _publisher) {
        this._publisher = _publisher;
    }

    public int get_favorite() {
        return _favorite;
    }

    public void set_favorite(int _favorite) {
        this._favorite = _favorite;
    }
}
