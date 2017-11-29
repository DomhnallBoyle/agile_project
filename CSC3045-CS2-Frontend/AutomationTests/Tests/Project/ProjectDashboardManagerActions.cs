using AutomationTests.PageTemplates;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems.ListBoxItems;

namespace AutomationTests.Tests.Project
{
    [TestFixture]
    public class ProjectDashboardManagerActions : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;
        private ProjectDashboardPage _projectDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupProjectDashboardManagerActions()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            
            LoginPage.Login("user2@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            
            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            projectListItem.Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());
            Assert.AreEqual("e2eForename2 e2eSurname2", _projectDashboardPage.ProjectManagerNameTextBlock.Text);
        }

        [Test]
        public void Test()
        {
            
        }
    }
}
