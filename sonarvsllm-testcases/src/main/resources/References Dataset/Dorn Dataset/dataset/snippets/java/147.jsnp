public class PluginGlobalPreferencesTab implements IGlobalPreferencesPanel {

    protected PluginQueryTokenizerPreferencesPanel _prefs = null;

    private JScrollPane _myscrolledPanel;

    private String _title = null;
    
    private String _hint = null;
    
    public PluginGlobalPreferencesTab(PluginQueryTokenizerPreferencesPanel prefsPanel) {
        _myscrolledPanel = new JScrollPane(prefsPanel);
        _prefs = prefsPanel;
    }

    public void initialize(IApplication app) {
        /* Do Nothing */
    }

    public void uninitialize(IApplication app) {
        /* Do Nothing */
    }

    public void applyChanges() {
        if (_prefs != null) {
            _prefs.applyChanges();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.squirrel_sql.client.util.IOptionPanel#getTitle()
     */
    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.squirrel_sql.client.util.IOptionPanel#getHint()
     */
    public String getHint() {
        return _hint;
    }
