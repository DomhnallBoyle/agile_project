using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White;
using TestStack.White.Factory;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests
{
    public abstract class BaseTestClass
    {
        private TestContext _context;

        public Window MainWindow { get; private set; }

        protected BaseTestClass()
        {
            var appPath = Path.Combine(_context.TestDirectory, "csc3045-cs2.exe");
            Application application = Application.Launch(appPath);
            MainWindow = application.GetWindow("MainWindow", InitializeOption.NoCache);
        }
    }
}
