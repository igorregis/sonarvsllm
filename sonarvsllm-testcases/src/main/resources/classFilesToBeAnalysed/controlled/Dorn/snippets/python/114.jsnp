            value = value.lower()
        self._set_instance_tag_cache(instance, value)

    def _save(self, signal, sender, instance):
        """
        Save tags back to the database
        """
        tags = self._get_instance_tag_cache(instance)
        if tags is not None:
            Tag.objects.update_tags(instance, tags)
