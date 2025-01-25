public abstract class AliasAction extends SquirrelAction
{
   /** Logger for this class. */
   private static ILogger s_log =
      LoggerController.createLogger(DeleteAliasAction.class);
   /** Internationalized strings for this class. */
   private static final StringManager s_stringMgr =
       StringManagerFactory.getStringManager(DeleteAliasAction.class);

   public AliasAction(IApplication app)
   {
      super(app);
   }

   protected void moveToFrontAndSelectAliasFrame()
   {
      IApplication app = getApplication();
      AliasesListInternalFrame tw = app.getWindowManager().getAliasesListInternalFrame();
      tw.moveToFront();
      try
      {
         tw.setSelected(true);
      }
      catch (PropertyVetoException ex)
      {
            //i18n[DeleteAliasAction.error.selectingwindow=Error selecting window]
         s_log.error(s_stringMgr.getString("DeleteAliasAction.error.selectingwindow"), ex);
      }
   }
