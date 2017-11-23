using GongSolutions.Wpf.DragDrop;
using GongSolutions.Wpf.DragDrop.Utilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace CSC3045_CS2.Utility
{
    public class PermissonDragHandler : IDragSource
    {
        private bool _permission;

        public PermissonDragHandler(bool permission)
        {
            this._permission = permission;
        }

        /// <summary>
        /// Queries whether a drag can be started.
        /// </summary>
        /// <param name="dragInfo">Information about the drag.</param>
        /// <remarks>
        /// To allow a drag to be started, the <see cref="DragInfo.Effects" /> property on <paramref name="dragInfo" />
        /// should be set to a value other than <see cref="DragDropEffects.None" />.
        /// </remarks>
        public virtual void StartDrag(IDragInfo dragInfo)
        {
            var itemCount = dragInfo.SourceItems.Cast<object>().Count();

            if (itemCount == 1)
            {
                dragInfo.Data = dragInfo.SourceItems.Cast<object>().First();
            }
            else if (itemCount > 1)
            {
                dragInfo.Data = TypeUtilities.CreateDynamicallyTypedList(dragInfo.SourceItems);
            }

            dragInfo.Effects = (dragInfo.Data != null) ? DragDropEffects.Copy | DragDropEffects.Move : DragDropEffects.None;
        }

        /// <summary>
        /// Determines whether this instance [can start drag] the specified drag information.
        /// </summary>
        /// <param name="dragInfo">The drag information.</param>
        /// <returns></returns>
        public virtual bool CanStartDrag(IDragInfo dragInfo)
        {
            return _permission;
        }

        /// <summary>
        /// Notifies the drag handler that a drop has occurred.
        /// </summary>
        /// <param name="dropInfo">Information about the drop.</param>
        public virtual void Dropped(IDropInfo dropInfo)
        {
        }

        /// <summary>
        /// Notifies the drag handler that a drag and drop operation has finished.
        /// </summary>
        /// <param name="operationResult">The operation result.</param>
        /// <param name="dragInfo">The drag information.</param>
        public virtual void DragDropOperationFinished(DragDropEffects operationResult, IDragInfo dragInfo)
        {
            // nothing here
        }

        /// <summary>
        /// Notifies the drag handler that a drag has been aborted.
        /// </summary>
        public virtual void DragCancelled()
        {
        }

        /// <summary>
        /// Notifies that an exception has occurred upon dragging.
        /// </summary>
        /// <param name="exception">The exception that occurrred.</param>
        /// <returns>
        /// Boolean indicating whether the exception is handled in the drag handler.
        /// False will rethrow the exception.
        /// </returns>
        public virtual bool TryCatchOccurredException(System.Exception exception)
        {
            return false;
        }
    }
}
