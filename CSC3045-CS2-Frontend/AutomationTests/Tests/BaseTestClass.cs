using AutomationTests.PageTemplates;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White;
using TestStack.White.Factory;
using TestStack.White.InputDevices;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests
{
    public abstract class BaseTestClass
    {
        protected Window MainWindow { get; private set; }

        protected Keyboard Keyboard { get; private set; }

        protected LoginPage LoginPage { get; private set; }
        
        [OneTimeSetUp]
        public void BaseTestFixtureSetup()
        {
            Keyboard = Keyboard.Instance;

            var appPath = Path.Combine(TestContext.CurrentContext.TestDirectory, "csc3045-cs2.exe");
            Application application = Application.Launch(appPath);
            MainWindow = application.GetWindow("Waterfall Systems", InitializeOption.NoCache);

            LoginPage = new LoginPage(MainWindow);
            Assert.IsTrue(LoginPage.IsCurrentPage());
        }

        [OneTimeTearDown]
        public void BaseTestFixtureTeardown()
        {
            MainWindow.Dispose();
        }
    }
}
