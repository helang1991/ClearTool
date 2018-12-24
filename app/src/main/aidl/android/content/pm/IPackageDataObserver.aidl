// IPackageDataObserver.aidl

// Declare any non-default types here with import statements

package android.content.pm;
/**
 * API for package data change related callbacks from the Package Manager.
 * statistics related to code, data, cache usage(TODO)
 * {@hide}
 */
oneway interface IPackageDataObserver {
   void onRemoveCompleted(in String packageName, boolean succeeded);
}