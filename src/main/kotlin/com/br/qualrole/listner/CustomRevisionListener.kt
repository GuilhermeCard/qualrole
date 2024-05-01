package com.br.qualrole.listner

import com.br.qualrole.domain.entity.audit.RevisionEntity
import org.hibernate.envers.RevisionListener

class CustomRevisionListener : RevisionListener {
    override fun newRevision(revisionEntity: Any?) {
        val currentThread = Thread.currentThread().stackTrace.last{ it.className.contains("com.br.qualrole")}
        val username = "${currentThread.fileName}#${currentThread.methodName}()"

        revisionEntity as RevisionEntity
        revisionEntity.username = username
    }

}