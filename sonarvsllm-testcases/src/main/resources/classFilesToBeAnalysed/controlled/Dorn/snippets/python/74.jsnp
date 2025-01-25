        topicObj = topicMgr.getOrCreateTopic(topicName)

        # don't care if topic not final: topicObj.getListeners()
        # will return nothing if not final but notification will still work

        topicObj.publish(data)

    def getMsgProtocol(self):
        return 'arg1'
