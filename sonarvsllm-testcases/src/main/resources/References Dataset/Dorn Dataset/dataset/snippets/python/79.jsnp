                v[0]=x+vx*dx-vy*dy
                v[1]=y+vx*dy+vy*dx

    def effect(self):
        if len(self.options.ids)<2:
            inkex.errormsg(_("This extension requires two selected paths."))
            return
        self.prepareSelectionList()
        self.options.wave = (self.options.kind=="Ribbon")
        if self.options.copymode=="Single":
            self.options.repeat =False
            self.options.stretch=False
        elif self.options.copymode=="Repeated":
            self.options.repeat =True
            self.options.stretch=False
        elif self.options.copymode=="Single, stretched":
            self.options.repeat =False
            self.options.stretch=True
        elif self.options.copymode=="Repeated, stretched":
            self.options.repeat =True
            self.options.stretch=True

        bbox=simpletransform.computeBBox(self.patterns.values())
                    
        if self.options.vertical:
            #flipxy(bbox)...
            bbox=(-bbox[3],-bbox[2],-bbox[1],-bbox[0])
            
        width=bbox[1]-bbox[0]
        dx=width+self.options.space
