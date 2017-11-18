using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public abstract class BasePage
    {
        protected Window MainWindow;

        public Label PageTitle
        {
            get { return MainWindow.Get<Label>("PageTitle"); }
        }

        public BasePage(Window window)
        {
            MainWindow = window;
        }

        public abstract bool IsCurrentPage();
    }
}
