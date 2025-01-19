                                continue
                            except:
                                continue

                # set the new working directory
                try:
                    for hypervisor in globals.GApp.dynagen.dynamips.values():
                        hypervisor.workingdir = self.projectWorkdir
                except lib.DynamipsError, msg:
                    QtGui.QMessageBox.critical(self, translate("Workspace", "Dynamips error %s: %s") % (self.projectWorkdir, unicode(msg)))
