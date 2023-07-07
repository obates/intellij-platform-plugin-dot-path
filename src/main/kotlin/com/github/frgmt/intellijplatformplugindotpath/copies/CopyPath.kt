package com.github.frgmt.intellijplatformplugindotpath.copies

import com.intellij.ide.actions.DumbAwareCopyPathProvider
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile

class CopyContentRootPathAction : DumbAwareCopyPathProvider() {
    override fun getPathToElement(project: Project,
                                  virtualFile: VirtualFile?,
                                  editor: Editor?): String? {
        return virtualFile?.let {
            ProjectFileIndex.getInstance(project).getModuleForFile(virtualFile, false)?.let { module ->
                ModuleRootManager.getInstance(module).contentRoots.mapNotNull { root ->
                    VfsUtilCore.getRelativePath(virtualFile, root)?.replace("/", ".")
                }.singleOrNull()
            }
        }
    }
}

class CopySourceRootPathAction : DumbAwareCopyPathProvider() {
    override fun getPathToElement(project: Project, virtualFile: VirtualFile?, editor: Editor?) =
        virtualFile?.let {
            VfsUtilCore.getRelativePath(virtualFile, ProjectFileIndex.getInstance(project).getSourceRootForFile(virtualFile) ?: return null)?.replace("/", ".")
        }
}