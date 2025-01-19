
    class Admin:
        list_display = ["name", "author"]


    def save(self):
        models.Model.save(self)
        self.tags = self.tag_list

        
    def _get_tags(self):
        return Tag.objects.get_for_object(self)
    
    
    def _set_tags(self, tag_list):
        Tag.objects.update_tags(self, tag_list)
        
        
    tags = property(_get_tags, _set_tags)

        
    def __str__(self):
        return "%s from %s" % (self.name, self.author)

    


    

class Release(models.Model):
