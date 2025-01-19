
class AttachmentViewerTest(test.wxTestCase):
    def setUp(self):
        settings = config.Settings(load=False)
        self.taskFile = persistence.TaskFile()
        attachments = attachment.AttachmentList()
        self.viewer = gui.viewer.AttachmentViewer(self.frame, self.taskFile, 
            settings, attachmentsToShow=attachments, 
            settingsSection='attachmentviewer')

    def tearDown(self):
        super(AttachmentViewerTest, self).tearDown()
        self.taskFile.close()
        self.taskFile.stop()

    def assertIcon(self, expectedIcon, anAttachment, **kwargs):
        self.assertEqual(self.viewer.imageIndex[expectedIcon], 
                         self.viewer.typeImageIndices(anAttachment, **kwargs)[wx.TreeItemIcon_Normal])
        
    def testTypeImageIndex_WhenFileDoesNotExist(self):
        fileAttachment = attachment.FileAttachment('whatever')
        self.assertIcon('fileopen_red', fileAttachment)
        
    def testTypeImageIndex_WhenFileDoesExist(self):
        fileAttachment = attachment.FileAttachment('whatever')
        self.assertIcon('fileopen', fileAttachment, exists=lambda filename: True)

    def testTypeImageIndex_UriAttachment(self):
        uriAttachment = attachment.URIAttachment('http://whatever.we')
        self.assertIcon('earth_blue_icon', uriAttachment)
