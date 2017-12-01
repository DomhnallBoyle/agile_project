using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WPFUIItems;

namespace AutomationTests.Tests.AcceptanceTest
{
    class CreateAcceptanceTest : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;

        private ProjectDashboardPage _projectDashboardPage;

        private ProductBacklogPage _productBacklogPage;

        private CreateUserStoryPage _createUserStoryPage;

        private UserStoryDetailsPage _userStoryDetailsPage;

        private CreateAcceptanceTestPage _createAcceptanceTestPage;

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            _productBacklogPage = new ProductBacklogPage(MainWindow);
            _userStoryDetailsPage = new UserStoryDetailsPage(MainWindow);
            _createUserStoryPage = new CreateUserStoryPage(MainWindow);
            _createAcceptanceTestPage = new CreateAcceptanceTestPage(MainWindow);

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            Assert.NotNull(projectListItem);
            projectListItem.Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ProductBacklogButton.Click();

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.enterCorrectStoryDetails("e2eUserStoryAcceptanceTest", "10", "e2eDescription");

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            var StoryListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStoryAcceptanceTest".Equals(item.Text));

            StoryListItem.Click();

            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyCreateAnAcceptanceTestAsAProductOwner()

        {
            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());

            _userStoryDetailsPage.CreateAcceptanceTestButton.Click();

            _createAcceptanceTestPage.enterCorrectAcceptanceDetails("e2eGiven1", "e2eWhen1", "e2eThen1");

            _createAcceptanceTestPage.CreateButton.Click();

            var messageBox = MessageBoxUtil.GetSuccessMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            var UserStoryAcceptanceTests = (WPFListItem)_userStoryDetailsPage.UserStoryAcceptanceTests.Items.Find(item => "e2eGiven1".Equals(item.Text));

            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyAccessCreateAAcceptanceTestAndCancel()
        {
              Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());

            _userStoryDetailsPage.CreateAcceptanceTestButton.Click();

            Assert.IsTrue(_createAcceptanceTestPage.IsCurrentPage());

            _createAcceptanceTestPage.CancelButton.Click();

            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailCreatingAnAcceptanceTestWithEmptyGiven()
        {
            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());

            _userStoryDetailsPage.CreateAcceptanceTestButton.Click();

            _createAcceptanceTestPage.enterEmptyGivenAcceptanceDetails("e2eWhen2", "e2eThen2");

            _createAcceptanceTestPage.CreateButton.Click();

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            _createAcceptanceTestPage.CancelButton.Click();

            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());

        }

        [Test]
        public void ShouldFailCreatingAnAcceptanceTestWithEmptyWhen()
        {
            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());

            _userStoryDetailsPage.CreateAcceptanceTestButton.Click();

            _createAcceptanceTestPage.enterEmptyWhenAcceptanceDetails("e2eGiven3", "e2eThen3");

            _createAcceptanceTestPage.CreateButton.Click();

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            _createAcceptanceTestPage.CancelButton.Click();

            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailCreatingAnAcceptanceTestWithEmptyThen()
        {
            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());

            _userStoryDetailsPage.CreateAcceptanceTestButton.Click();

            _createAcceptanceTestPage.enterEmptyThenAcceptanceDetails("e2eGiven4", "e2eWhen4");

            _createAcceptanceTestPage.CreateButton.Click();

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            _createAcceptanceTestPage.CancelButton.Click();

            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());
        }
    }
}
