                for j in range (1, self.options.r_subdivs):
                    draw_SVG_circle(i*dr-j*dr/self.options.r_subdivs, #minor div circles
                                    0, 0, self.options.r_subdivs_th, 'none',
                                    'MinorDivCircle'+str(i)+':R'+str(i*dr), grid)
        
        if self.options.a_divs == self.options.a_divs_cent: #the lines can go from the centre to the edge
            for i in range(0, self.options.a_divs):
                draw_SVG_line(0, 0, rmax*sin(i*dtheta), rmax*cos(i*dtheta), 
                              self.options.a_divs_th, 'RadialGridline'+str(i), grid)
        
        else: #we need separate lines
            for i in range(0, self.options.a_divs_cent): #lines that go to the first circle
                draw_SVG_line(0, 0, dr*sin(i*dtheta), dr*cos(i*dtheta), 
                              self.options.a_divs_th, 'RadialGridline'+str(i), grid)
        
            dtheta = 2 * pi / self.options.a_divs #work out the angle change for outer lines
            
            for i in range(0, self.options.a_divs): #lines that go from there to the edge
                draw_SVG_line(  dr*sin(i*dtheta+pi/2.0),   dr*cos(i*dtheta+pi/2.0), 
                              rmax*sin(i*dtheta+pi/2.0), rmax*cos(i*dtheta+pi/2.0), 
                              self.options.a_divs_th, 'RadialGridline'+str(i), grid)
        
        if self.options.a_subdivs > 1: #draw angular subdivs
            for i in range(0, self.options.a_divs): #for each major divison
                for j in range(1, self.options.a_subdivs): #draw the subdivisions
                    angle = i*dtheta-j*dtheta/self.options.a_subdivs+pi/2.0 # the angle of the subdivion line
                    draw_SVG_line(dr*self.options.a_subdivs_cent*sin(angle),
                                  dr*self.options.a_subdivs_cent*cos(angle), 
                                  rmax*sin(angle), rmax*cos(angle), 
                                  self.options.a_subdivs_th, 'RadialMinorGridline'+str(i), grid)
        
        if self.options.c_dot_dia <> 0: #if a non-zero diameter, draw the centre dot
            draw_SVG_circle(self.options.c_dot_dia /2.0,
                            0, 0, 0, '#000000', 'CentreDot', grid)
        
        if self.options.a_labels == 'deg':
            label_radius = rmax+self.options.a_label_outset  #radius of label centres
            label_size = self.options.a_label_size
            numeral_size = 0.73*label_size #numerals appear to be 0.73 the height of the nominal pixel size of the font in "Sans"
            
            for i in range(0, self.options.a_divs):#self.options.a_divs): #radial line labels
                draw_SVG_label_centred(sin(i*dtheta+pi/2.0)*label_radius,        #0 at the RHS, mathematical style
                                       cos(i*dtheta+pi/2.0)*label_radius+ numeral_size/2.0, #centre the text vertically 
                                       str(i*360/self.options.a_divs), 
                                       label_size, 'Label'+str(i), grid)

if __name__ == '__main__':
    e = Grid_Polar()
    e.affect()

