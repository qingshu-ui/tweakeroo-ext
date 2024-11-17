package com.github.shu.tweakerooExt.utils

import org.reflections.Reflections
import org.reflections.scanners.Scanners
import java.lang.reflect.Method
import kotlin.reflect.KClass

object ReflectInit {

    private val reflections: MutableList<Reflections> = arrayListOf()

    @JvmStatic
    fun registerPackage(vararg packageName: String) {
        for (p in packageName) {
            if (p.isEmpty() || p.isBlank()) return
            reflections.add(Reflections(p, Scanners.MethodsAnnotated))
        }
    }

    @JvmStatic
    fun init(annotation: KClass<out Annotation>) {

        for (reflection in reflections) {
            val initTask = reflection.getMethodsAnnotatedWith(annotation.java)
            if (initTask.isEmpty()) return

            val byClass = initTask.groupBy { it.declaringClass }
            val left: HashSet<Method> = HashSet(initTask)

            while (true) {
                val method = left.firstOrNull() ?: break
                reflectInit(method, annotation, left, byClass)
            }
        }
    }

    private fun reflectInit(
        task: Method,
        annotation: KClass<out Annotation>,
        left: HashSet<Method>,
        byClass: Map<Class<*>?, List<Method>>
    ) {
        left.remove(task)
        for (clazz in getDependencies(task, annotation)) {
            val methods = byClass[clazz.java] ?: continue
            for (m in methods) {
                if (m in left) reflectInit(m, annotation, left, byClass)
            }
        }

        try {
            task.invoke(null)
        } catch (e: Exception) {
            if (e is NullPointerException) {
                throw RuntimeException("Method \"${task.name}\" using Init annotation from non-static context}")
            }
        }
    }

    private fun <T : Annotation> getDependencies(task: Method, annotation: KClass<T>): Array<KClass<*>> {
        return when (val init: T = task.getAnnotation(annotation.java)) {
            is PreInit -> init.dependencies
            is PostInit -> init.dependencies
            else -> emptyArray()
        }
    }
}